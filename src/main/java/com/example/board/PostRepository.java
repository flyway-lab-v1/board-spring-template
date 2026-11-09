package com.example.board;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    record PostView(Long id, String title, String content, String author) {
    }

    @Query("""
            SELECT new com.example.board.PostRepository$PostView(p.id, p.title, p.content, u.name)
              FROM Post p JOIN User u ON u.id = p.userId
             ORDER BY p.id DESC
            """)
    List<PostView> findAllWithAuthor();
}
