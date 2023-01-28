package com.catchmind.catchtable.controller;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class NoticeController {

    // 공지사항 리스트 페이지
    @GetMapping("/notice")
    public ModelAndView notice() {
        return new ModelAndView("notice/notice");
    }

    //공지사항 상세
    @GetMapping("/notice/content")
    public ModelAndView noticeContent() {
        return new ModelAndView("notice/notice_review");
    }

    // 이용약관
    @GetMapping("/support/tmp1")
    public ModelAndView supportTmp1() {
        return new ModelAndView("notice/termOfService1");
    }

    //개인정보 처리방침
    @GetMapping("/support/tmp2")
    public ModelAndView supportTmp2() {
        return new ModelAndView("notice/termOfService2");
    }
    
    // 위치정보
    @GetMapping("/support/tmp3")
    public ModelAndView supportTmp3() {
        return new ModelAndView("notice/termOfService3");
    }

    // 1대1문의 리스트
    @GetMapping("/support/contact")
    public ModelAndView contact() {
        return new ModelAndView("notice/contact1");
    }

    // 1대1문의 작성
    @GetMapping("/support/contact/write")
    public ModelAndView contactWrite() {
        return new ModelAndView("notice/contact2");
    }
    
    // 개선제안 리스트
    @GetMapping("/support/improve")
    public ModelAndView improve() {
        return new ModelAndView("notice/improve1");
    }
    
    //개선제안 작성
    @GetMapping("/support/improve/write")
    public ModelAndView improveWrite() {
        return new ModelAndView("notice/improve2");
    }

    // 신고내역
    @GetMapping("/report/list")
    public ModelAndView reportList() {
        return new ModelAndView("notice/report_list");
    }

    // 리뷰 신고
    @GetMapping("/report/review")
    public ModelAndView reportReview() {
        return new ModelAndView("notice/report_review");
    }    

    // 댓글 신고
    @GetMapping("/report/comment")
    public ModelAndView reportReply() {
        return new ModelAndView("notice/report_comment");
    }
}
