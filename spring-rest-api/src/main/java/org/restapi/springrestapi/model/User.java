package org.restapi.springrestapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.restapi.springrestapi.config.PasswordEncoder;
import org.restapi.springrestapi.dto.user.PatchProfileRequest;
import org.restapi.springrestapi.dto.user.RegisterUserRequest;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
	private Long id;
	private String email;
	private String password;
	private String nickname;
	private String profileImage; // nullable

    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST)
    private List<Post> posts = new ArrayList<>();

    public static User from(
		RegisterUserRequest command,
		PasswordEncoder passwordEncoder
	) {
		return User.builder()
			.email(command.getEmail())
			.password(passwordEncoder.encode(command.getPassword()))
			.nickname(command.getNickname())
			.profileImage(command.getProfileImage())
			.build();
	}
	public void updateProfile(PatchProfileRequest command) {
		this.nickname = command.getNickname();
		if (command.getProfileImage() != null) {
			this.profileImage = command.getProfileImage();
		}
	}
	public void updatePassword(String password, PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(password);
	}

    public void addPost(Post post) {
        if (post == null) return;
        if (posts.contains(post)) return;
        this.posts.add(post);
        post.changeAuthor(this);
    }

    public void removePost(Post post) {
        if (post == null) return;
        if (!posts.contains(post)) return;
        this.posts.remove(post);
        post.changeAuthor(null);
    }
}
