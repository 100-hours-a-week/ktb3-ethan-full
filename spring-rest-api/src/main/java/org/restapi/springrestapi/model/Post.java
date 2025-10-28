package org.restapi.springrestapi.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.restapi.springrestapi.dto.post.PatchPostRequest;
import org.restapi.springrestapi.dto.post.RegisterPostRequest;

import lombok.Builder;
import lombok.Getter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String content;
	private String image; // nullable

	private List<Long> likeUsers;
	private List<Long> comments;
	private int viewCount;

	LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;


    public static Post from(User author, RegisterPostRequest command) {
		return Post.builder()
			.author(author)
			.title(command.title())
			.content(command.content())
			.image(command.image())
			.createdAt(LocalDateTime.now())
			.build();
	}
	public static Post from(PatchPostRequest command, Post prevPost) {
		return prevPost.toBuilder()
			.title(command.title())
			.content(command.content())
			.image(command.image())
			.build();
	}

    protected void changeAuthor(User author) {
        this.author = author;
        if (author != null) {
            author.getPosts().remove(this);
        }

    }
}
