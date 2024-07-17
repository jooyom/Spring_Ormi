package com.example.july08.July16;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users") //유저에대한 요청을 담는 ? 컨트롤러?
public class UserController {

    public UserController() {
    }

    @GetMapping
    public List<User> getAllUsers(){
        //전체 사용자 목록 조회
        return null;
    }

    //새로운 유저 생성은 Post
    @PostMapping
    public User createUser(@RequestBody User user){
        //requestBody는 http 데이터를 쏠 때 데이털르 body에 담아서 가죠옴 여기서는 user로 가져옴
        //사용자 생성 로직인데
        return null;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id){
        //특정 사용자를 찾기
        return null;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody User updqteUser){
        //유저 정보 업데이트 로직(전부 변경)
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        //사용자 삭제 메서드
        //return 이 필요없다.
    }


}
