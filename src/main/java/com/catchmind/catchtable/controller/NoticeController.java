package com.catchmind.catchtable.controller;


import com.catchmind.catchtable.domain.Ask;
import com.catchmind.catchtable.dto.*;
import com.catchmind.catchtable.dto.network.request.AskRequest;
import com.catchmind.catchtable.dto.network.request.ImprovementRequest;
import com.catchmind.catchtable.dto.security.CatchPrincipal;
import com.catchmind.catchtable.repository.*;
import com.catchmind.catchtable.service.NoticeService;
import com.catchmind.catchtable.service.PaginationService;
import com.catchmind.catchtable.service.ProfileLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class NoticeController {

    private final AskRepository askRepository;
    private final NoticeRepository noticeRepository;
    private final NoticeService noticeService;
    private final ProfileLogicService profileLogicService;
    private final ImprovementRepository improvementRepository;
    private final DeclareReviewRepository declareReviewRepository;
    private final DeclareCommentRepository declareCommentRepository;
    private final PaginationService paginationService;


    // 공지사항 리스트 페이지
    @GetMapping("/notice")
    public String notice(Model model) {
        List<NoticeDto> noticeDtoList = noticeRepository.findAll().stream().map(NoticeDto::from).toList();
        model.addAttribute("notice", noticeDtoList);
        return "notice/notice";
    }

    //공지사항 상세
    @GetMapping("/notice/content/{noIdx}")
    public String noticeContent(Model model, @PathVariable Long noIdx) {
//        List<NoticeDto> noticeDtoList = noticeRepository.findAll().stream().map(NoticeDto::from).toList();
        NoticeDto noticeDto = noticeService.getNotice(noIdx);
        model.addAttribute("content", noticeDto);
//        model.addAttribute("content", noticeDtoList);
        return "notice/notice_review";
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
    public String contact(Model model, @AuthenticationPrincipal CatchPrincipal catchPrincipal, @PageableDefault(size=10, sort="askIdx", direction = Sort.Direction.DESC) Pageable pageable) {
        Long prIdx = catchPrincipal.prIdx();
//        List<AskDto> askDtoList = askRepository.findAllByProfile_PrIdx(prIdx).stream().map(AskDto::from).toList();

        Page<Ask> asks = noticeService.list(pageable, prIdx);
        List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), asks.getTotalPages());

        model.addAttribute("notice", asks);
        model.addAttribute("paginationBarNumbers", barNumbers);
//        model.addAttribute("notice", askDtoList);

        return "notice/contact1";
    }

    // 1대1문의 작성
    @GetMapping("/support/contact/write")
    public String contactWrite(Model model, @AuthenticationPrincipal CatchPrincipal catchPrincipal) {
        Long prIdx = catchPrincipal.prIdx();
        model.addAttribute("prIdx", prIdx);
        return "notice/contact2";
    }

    @GetMapping("/support/contact/write/modify/{askIdx}")
    public String modify(Model model, @PathVariable("askIdx") Long askIdx) {
        AskDto askDto = noticeService.getDetail(askIdx);
        model.addAttribute("askDto", askDto);
        return "notice/contactModify";
    }

    // 1:1문의 수정
    @PutMapping("/support/contact/write/modify/{askIdx}")
    public String modifyWrite(Model model, @PathVariable("askIdx") Long askIdx, AskRequest request) {
        System.out.println(request);
        noticeService.updateWrite(askIdx, request);
//        model.addAttribute("askDto", askDto);
        return "redirect:/support/contact";
    }

    @GetMapping("/support/contact/write/impModify/{impIdx}")
    public String impModify(Model model, @PathVariable("impIdx") Long impIdx) {
        ImprovementDto improvementDto = noticeService.getImpDetail(impIdx);
        model.addAttribute("improvementDto", improvementDto);
        return "notice/improveModify";
    }

    // 개선사항 문의 수정
    @PutMapping("/support/contact/write/impModify/{impIdx}")
    public String impModifyWrite(Model model, @PathVariable("impIdx") Long impIdx, ImprovementRequest request) {
        System.out.println(request);
        noticeService.updateImpWrite(impIdx, request);
//        model.addAttribute("askDto", askDto);
        return "redirect:/support/improve";
    }

    // 1:1문의 삭제
    @DeleteMapping("/support/contact/write/delete/{askIdx}")
    public String delete(Model model, @PathVariable("askIdx") Long askIdx) {
        noticeService.deletePost(askIdx);
        return "redirect:/support/contact";
    }

    // 개선사항 삭제
    @DeleteMapping("/support/contact/write/impDelete/{impIdx}")
    public String impDelete(Model model, @PathVariable("impIdx") Long impIdx) {
        noticeService.impDeletePost(impIdx);
        return "redirect:/support/improve";
    }

    // 1:1문의 등록
    @PostMapping("/support/contact/write")
    public String contactWrite(AskRequest askRequest) {
        System.out.println("🍌" + askRequest);
        noticeService.saveFile(askRequest);
        return "redirect:/support/contact";
    }

    // 개선제안 리스트
    @GetMapping("/support/improve")
    public String improve(Model model, @AuthenticationPrincipal CatchPrincipal catchPrincipal) {
        Long prIdx = catchPrincipal.prIdx();
        List<ImprovementDto> improvementDtoList = improvementRepository.findAllByProfile_PrIdx(prIdx).stream().map(ImprovementDto::from).toList();
        model.addAttribute("notice", improvementDtoList);
        return "notice/improve1";
    }

    //개선제안 작성
    @GetMapping("/support/improve/write")
    public String improveWrite(Model model, @AuthenticationPrincipal CatchPrincipal catchPrincipal) {
        Long prIdx = catchPrincipal.prIdx();
        model.addAttribute("prIdx", prIdx);
        return "notice/improve2";
    }

    // 개선제안 작성 내용 저장
    @PostMapping("/support/improve/write")
    public String improveWrite(ImprovementRequest improvementRequest) {
        noticeService.saveImprovementFile(improvementRequest);
        return "redirect:/support/improve";     // 돌아가자마자 status값이 null 이라서 리스트페이지에서 오류인듯   // support로 수정
    }
    // 리뷰 신고내역
    @GetMapping("/report/review/list")
    public String reportList(Model model, @AuthenticationPrincipal CatchPrincipal catchPrincipal) {
        Long prIdx = catchPrincipal.prIdx();
        List<DeclareReviewDto> declareReviewDto = declareReviewRepository.findAllByProfile_PrIdx(prIdx).stream().map(DeclareReviewDto::from).toList();
        model.addAttribute("notice", declareReviewDto);
        return "notice/report_list";
    }

    @GetMapping("/report/comment/list")
    public String reportCommentList(Model model, @AuthenticationPrincipal CatchPrincipal catchPrincipal) {
        Long prIdx = catchPrincipal.prIdx();
        List<DeclareCommentDto> declareCommentDto = declareCommentRepository.findAllByProfile_PrIdx(prIdx).stream().map(DeclareCommentDto::from).toList();
        model.addAttribute("notice", declareCommentDto);
        return "notice/report_list_comment";
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
