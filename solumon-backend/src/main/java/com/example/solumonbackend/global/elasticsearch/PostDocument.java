package com.example.solumonbackend.global.elasticsearch;

import com.example.solumonbackend.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "posts")
@Mapping(mappingPath = "elasticsearch/search-mapping.json")
@Setting(settingPath = "elasticsearch/search-setting.json")
public class PostDocument {

  @Id
  private Long id;
  private String title;
  private String content;
  private String nickname;
  private String imageUrl;
  private int voteCount;
  private int chatCount;
  private String endAt;
  private String createdAt;
  private List<String> tags;

  public static PostDocument createPostDocument(Post post, List<String> tags) {
    return PostDocument.builder()
        .id(post.getPostId())
        .title(post.getTitle())
        .content(post.getContents())
        .nickname(post.getMember().getNickname())
        .imageUrl(post.getThumbnailUrl())
        .voteCount(post.getVoteCount())
        .chatCount(post.getChatCount())
        .endAt(post.getEndAt().toString())
        .createdAt(post.getCreatedAt().toString())
        .tags(tags)
        .build();
  }

  public void setVoteCount(int voteCount) {
    this.voteCount = voteCount;
  }

  public PostDocument updatePostDocument(Post post, List<String> tags) {
    this.title = post.getTitle();
    this.content = post.getContents();
    this.imageUrl = post.getThumbnailUrl();
    this.voteCount = post.getVoteCount();
    this.chatCount = post.getChatCount();
    this.endAt = post.getEndAt().toString();
    this.createdAt = post.getCreatedAt().toString();
    this.tags = tags;

    return this;
  }

}
