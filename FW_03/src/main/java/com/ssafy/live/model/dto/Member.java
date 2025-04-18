package com.ssafy.live.model.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

//롬복 사용
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
public class Member {
    private int mno;
    private @NonNull String name;
    private @NonNull String email;
    private @NonNull String password;
    private String role;
    private int age;
    
    // 주소 목록
    //ToString, EqualsAndHashCode 할 때 얘는 빼줘
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    //계속 쓸거니까 null로 바꾸지마~
    @Builder.Default
    private List<Address> addresses = new ArrayList<>();

//    public Member() {
//    }
//
//    public Member(String name, String email, String password) {
//        this(0, name, email, password, null);
//    }
//
//    public Member(int mno, String name, String email, String password, String role) {
//        this.mno = mno;
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.role = role;
//    }
//
//    public int getMno() {
//        return mno;
//    }
//
//    public void setMno(int mno) {
//        this.mno = mno;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//
//    public List<Address> getAddresses() {
//        return addresses;
//    }
//
//    public void setAddresses(List<Address> addresses) {
//        this.addresses = addresses;
//    }
//
//    @Override
//    public String toString() {
//        return "Member [mno=" + mno + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role + "]";
//    }

}
