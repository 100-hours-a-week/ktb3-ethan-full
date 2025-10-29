package org.restapi.springrestapi.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.restapi.springrestapi.dto.comment.RegisterCommentRequest;

import lombok.Builder;
import lombok.Getter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(nullable = false)
	private String content;

    @Column(nullable = false)
	private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

	public static Comment from(RegisterCommentRequest command, Post post) {
		return Comment.builder()
			.post(post)
			.content(command.content())
			.createdAt(LocalDateTime.now())
			.build();
	}

	public void updateContent(String newContent) {
		if (newContent != null) {
			this.content = newContent;
		}
	}
}
