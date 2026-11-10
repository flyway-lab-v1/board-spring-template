package com.example.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

// 로컬 DB(board)에 Flyway CLI로 마이그레이션을 적용한 뒤 실행한다.
// ddl-auto: validate 이므로 테이블이 없거나 다르면 애플리케이션이 뜨지 않아 이 테스트가 실패한다.
// @Transactional: 테스트가 넣은 데이터는 끝나면 되돌린다. 화면에서 넣은 기존 글·회원은 지우지 않는다.
@SpringBootTest
@Transactional
class PostApiTests {

    @Autowired PostRepository postRepository;
    @Autowired JdbcTemplate jdbc;

    @Test
    void 글을_저장하면_작성자와_함께_조회된다() {
        jdbc.update("INSERT INTO users (email, name) VALUES ('test-kim@example.com', '김팀원')");
        Long userId = jdbc.queryForObject("SELECT id FROM users WHERE email = 'test-kim@example.com'", Long.class);

        Post saved = postRepository.save(new Post(userId, "첫 글", "안녕하세요"));

        var mine = postRepository.findAllWithAuthor().stream()
                .filter(p -> p.id().equals(saved.getId()))
                .toList();
        assertThat(mine).hasSize(1);
        assertThat(mine.get(0).title()).isEqualTo("첫 글");
        assertThat(mine.get(0).author()).isEqualTo("김팀원");
    }
}
