package com.example.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

// 로컬 DB(board)에 Flyway CLI로 마이그레이션을 적용한 뒤 실행한다.
// ddl-auto: validate 이므로 테이블이 없거나 다르면 애플리케이션이 뜨지 않아 이 테스트가 실패한다.
@SpringBootTest
@DirtiesContext
class PostApiTests {

    @Autowired PostRepository postRepository;
    @Autowired JdbcTemplate jdbc;

    @Test
    void 글을_저장하면_작성자와_함께_조회된다() {
        jdbc.update("DELETE FROM posts");
        jdbc.update("DELETE FROM users");
        jdbc.update("INSERT INTO users (email, name) VALUES ('kim@example.com', '김팀원')");
        Long userId = jdbc.queryForObject("SELECT id FROM users WHERE email = 'kim@example.com'", Long.class);

        postRepository.save(new Post(userId, "첫 글", "안녕하세요"));

        var posts = postRepository.findAllWithAuthor();
        assertThat(posts).hasSize(1);
        assertThat(posts.get(0).title()).isEqualTo("첫 글");
        assertThat(posts.get(0).author()).isEqualTo("김팀원");
    }
}
