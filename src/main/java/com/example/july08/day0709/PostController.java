package com.example.july08.day0709;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {


    private List<Post> posts = new ArrayList<>();
    private Long nextId = 1L;

    //포스트 조회기능
    @GetMapping
    public String list(Model model){
        model.addAttribute ("posts", posts);
        return "post/list";
    }

    //포스트 생성폼
    @GetMapping("/new")
    public String newPostForm(Model model){
       // model.addAttribute("post", new Post());
        //굳이 new post로 만들 필요는 없는데 있어도 동작은 잘 합니닷
        return "post/form";
    }


    //게시물 생성폼 기준으로 생성
    @PostMapping
    public String savePost(@ModelAttribute Post post){
        post.setId(nextId++); //iD를 증가시키는 이유는 Id 값이 유일한 pk이기때문
        post.setCreateAt(LocalDateTime.now()); //현재(생성시) 시간
        posts.add(post); //posts 리스트에 post 객체 추가
        return "redirect:/posts"; //300번대?
    }

    @GetMapping("/{id}") // 중괄호 !
    public String detail(@PathVariable("id") Long id, Model model){
                       //경로 변수
        Post post = posts.stream()
                .filter(p->p.getId()==id)
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException());

            model.addAttribute("post", post);
            return "post/detail";
    }

   // @DeleteMapping 있는데 post로 해볼게욥
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id){
        //저흰 하드딜리트 할겁니닷
        posts.removeIf(post-> post.getId()==id); //arraylist 컬렉션 요소 removeIf 와 람다식
        return "redirect:/posts";
        //삭제만 하는거라 id가 지워졌다면? 이라는 방어 코드도 필요하겠죠? 예외처리가 중요합니다
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model){
        Post post = posts.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());

        model.addAttribute("post", post);
        return "post/edit";
        //detail과 비슷하죠? 수정된 화면에 기존에 입력했던 내용이 나와야해서 포스트를 한번 찾는다.
        //id로 찾은 포스트에 대한 내용이 보여줘야ㅑ필터가 찾아주는역할이다.
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, @ModelAttribute Post updatePost){
        //아이디로 포스트를 찾고 updatePost에 있는 데이터로 변경해주어야 한다
        Post post = posts.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());
        post.setTitle(updatePost.getTitle());
        // set메서드로 업데이트 객체의 title 값으로 변경
        post.setContent(updatePost.getContent());
        // set 메서드로 엡데이터ㅡ 객체의 content 값으로 변경
        return "redirect:/posts/{id}";

    }
}
