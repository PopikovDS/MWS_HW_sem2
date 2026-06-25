package com.mipt.popikovdmitriy.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TaskStatisticsJdbcService {

    private final JdbcTemplate jdbc;

    public TaskStatisticsJdbcService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Map<String, Long> getTasksCountByCompletedStatus() {
        String sql = "SELECT completed, count(*) as cnt FROM tasks GROUP BY completed";
        return jdbc.query(sql, rs -> {
            Map<String, Long> m = new HashMap<>();
            while (rs.next()) {
                boolean completed = rs.getBoolean("completed");
                long cnt = rs.getLong("cnt");
                m.put(String.valueOf(completed), cnt);
            }
            return m;
        });
    }

    public Map<String, Long> getTasksCountByPriority() {
        String sql = "SELECT priority, COUNT(*) as cnt FROM tasks GROUP BY priority";
        return jdbc.query(sql, rs -> {
            Map<String, Long> result = new HashMap<>();
            while (rs.next()) {
                String priority = rs.getString("priority");
                Long count = rs.getLong("cnt");
                result.put(priority, count);
            }
            return result;
        });
    }

}
