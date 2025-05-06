package com.springboot.biz.saramin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.biz.m3user.M3Repository;
import com.springboot.biz.m3user.M3Service;
import com.springboot.biz.m3user.M3User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Service
public class SaraminService {

    @Value("${saramin.api-key}")   //env 설정 해주세요!!!!!!
    private String API_KEY;

    private final M3Service m3Service;
    private final M3Repository m3Repository;
    private final SaraminRepository saraminRepository;
    private final UserSaraminRepository userSaraminRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();//json변환을 위한 키

    public List<Map<String, String>> getJobsFromApi(String keywords) throws Exception {
        if (keywords == null || keywords.trim().isEmpty()) {
            keywords = ""; //기본값으로 검색 ㅇ@삭제해도됨
        }

        String encodedKeyword = URLEncoder.encode(keywords, "UTF-8"); //한글 + 5개만 보이게
        String apiUrl = "https://oapi.saramin.co.kr/job-search?access-key=" + API_KEY + "&keywords=" + encodedKeyword + "&count=100";
        //localhost:8080/saramin/search?keywords=직업
        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");

        int responseCode = con.getResponseCode();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                responseCode == 200 ? con.getInputStream() : con.getErrorStream()
        ));

        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        br.close();
        System.out.println("c출력되라--------------------------");
        System.out.println("@@@@@@@@API_KEY@@@@@@@@ = " + API_KEY);

        JsonNode root = objectMapper.readTree(response.toString());
        JsonNode jobs = root.path("jobs").path("job");
        //readTree json값을 자바 겍체로 바꾸기위해(DTO쓰지안ㅇㄴㅎㄱ)
        List<Map<String, String>> result = new ArrayList<>();
        for (JsonNode job : jobs) {
            String company = job.path("company").path("name").asText();
            String position = job.path("position").path("title").asText();

            String openingTimestampRaw = job.path("opening-timestamp").asText();
            String expirationTimestampRaw = job.path("expiration-timestamp").asText();
            /*posttingDate 경로 api가 주는 이름과 맞춰서 단순 숫자값을 날짜 문자열로 바꾸기 위해 작업한것
             convertTimestamp 메서드는 밑에 선언함 젠장*/
            String postingDate = convertTimestamp(openingTimestampRaw);
            String expirationDate = convertTimestamp(expirationTimestampRaw);

            String active = job.path("active").asText();
            String closeType = job.path("close-type").asText();
            String urlStr = job.path("url").asText();
            System.out.println("출력 확인용@@@@@@@@company = " + company);
            System.out.println("확인용@@@@@@@@position = " + position);
            System.out.println("확인용@@@@@@@@closeType = " + closeType);
            result.add(Map.of(
                    "company", company,
                    "position", position,
                    "posting-date", postingDate,
                    "expiration-date", expirationDate,
                    "active", active,
                    "close-type", closeType,
                    "url", urlStr
            ));
        }

        return result;
    }
    @Transactional
    public List<Saramin> getJobsFromApiAndSave(String keywords) throws Exception {
        if (keywords == null || keywords.trim().isEmpty()) {
            keywords = "";
        }

        String encodedKeyword = URLEncoder.encode(keywords, "UTF-8");
        String apiUrl = "https://oapi.saramin.co.kr/job-search?access-key=" + API_KEY + "&keywords=" + encodedKeyword + "&count=30";

        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");

        int responseCode = con.getResponseCode();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                responseCode == 200 ? con.getInputStream() : con.getErrorStream()
        ));

        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        br.close();

        JsonNode root = objectMapper.readTree(response.toString());
        JsonNode jobs = root.path("jobs").path("job");

        List<Saramin> savedJobs = new ArrayList<>();
        for (JsonNode job : jobs) {
            Saramin s = new Saramin();
            s.setCompanyName(job.path("company").path("name").asText());
            s.setPositionTitle(job.path("position").path("title").asText());
            s.setPostingDate(convertTimestamp(job.path("opening-timestamp").asText()));
            s.setExpirationDate(convertTimestamp(job.path("expiration-timestamp").asText()));
            s.setActive(job.path("active").asText());
            s.setCloseType(job.path("close-type").asText());
            s.setUrl(job.path("url").asText());

            Saramin saved = saraminRepository.save(s);
            savedJobs.add(saved);
        }

        return savedJobs;
    }
    /*즐겨찾기 위한*/
    public List<SaraminDto> getJobsFromApiDto(String keywords) throws Exception {
        List<Map<String, String>> rawJobs = getJobsFromApi(keywords);
        List<SaraminDto> result = new ArrayList<>();

        for (Map<String, String> job : rawJobs) {
            SaraminDto dto = new SaraminDto();
            dto.setCompanyName(job.get("company"));
            dto.setPositionTitle(job.get("position"));
            dto.setPostingDate(job.get("posting-date"));
            dto.setExpirationDate(job.get("expiration-date"));
            dto.setActive(job.get("active"));
            dto.setCloseType(job.get("close-type"));
            dto.setUrl(job.get("url"));

            // ✅ 날짜 구간 추가 (예: ["2025-05-01", "2025-05-02", "2025-05-03"])
            List<String> dateRange = calculateDateRange(job.get("posting-date"), job.get("expiration-date"));
            dto.setDateRange(dateRange);


            result.add(dto);
        }

        return result;
    }


    private List<String> calculateDateRange(String startStr, String endStr) {
        List<String> range = new ArrayList<>();
        try {
            LocalDate start = LocalDate.parse(startStr);
            LocalDate end = LocalDate.parse(endStr);
            while (!start.isAfter(end)) {
                range.add(start.toString()); // yyyy-MM-dd 형식으로 추가
                start = start.plusDays(1);
            }
        } catch (Exception e) {
            // 날짜 파싱 실패시 무시
        }
        return range;
    }






// timestamp를 yyyy-MM-dd로 변환하는 메서드
private String convertTimestamp(String timestampStr) {
    try {
        long timestamp = Long.parseLong(timestampStr);
        Date date = new Date(timestamp * 1000L); // 초 단위라서 1000 곱함 ㅅㅂ
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    } catch (Exception e) {
        return "-";
    }
}

   public void apply(Integer id, boolean applied){
        Saramin saramin = saraminRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("공고 없음"));
        saramin.setApplied(applied);
        saraminRepository.save(saramin);
   }


    public void updateAppliedStatus(String username, Integer saraminId, boolean applied) {
        M3User user = m3Service.findByUsername(username);
        Saramin saramin = saraminRepository.findById(saraminId)
                .orElseThrow(() -> new RuntimeException("공고 없음"));

        UserSaramin userSaramin = userSaraminRepository.findByM3UserAndSaramin(user, saramin)
                .orElseGet(() -> {
                    System.out.println("신규 UserSaramin 생성");
                    UserSaramin newEntry = new UserSaramin();
                    newEntry.setM3User(user);
                    newEntry.setSaramin(saramin);
                    return newEntry;
                });

        userSaramin.setApplied(applied);
        userSaraminRepository.save(userSaramin);
        System.out.println("지원 여부 저장됨 -> applied: " + applied);
    }

}