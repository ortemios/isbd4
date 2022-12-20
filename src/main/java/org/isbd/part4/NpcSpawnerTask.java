package org.isbd.part4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NpcSpawnerTask {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Scheduled(fixedRate = 5000, initialDelay = 0)
    public void reportCurrentTime() {
        jdbcTemplate.execute("call spawn_npc();");
    }
}
