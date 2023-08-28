package com.example.examboard.controller;

import com.example.examboard.dto.ArticleCommentDto;
import com.example.examboard.dto.ArticleForm;
import com.example.examboard.dto.SearchParam;
import com.example.examboard.entity.Article;
import com.example.examboard.repository.ArticleRepository;
import com.example.examboard.service.ArticleService;
import com.example.examboard.service.PaginationService;
import com.example.examboard.service.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/articles")
public class BoardController {

    @Autowired
    PaginationService paginationService;

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleRepository articleRepository;

    @GetMapping("/lists")
    public String boardList(Model model, @RequestParam(value = "searchType", required = false)String searchType,
                            @RequestParam(value = "searchValue",required = false)String searchValue,
                            @PageableDefault(page=0, size=10, sort="createdAt",
                                    direction= Sort.Direction.DESC)
                            Pageable pageable
                            ){

        SearchParam searchParam = new SearchParam();
        searchParam.setSearchType(searchType);
        searchParam.setSearchValue(searchValue);

        Page<Article> paging = (Page<Article>) articleService.boardList(pageable, searchType, searchValue);
        int totalPage = paging.getTotalPages();
        //페이지블럭 처리
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(),
                totalPage);

        model.addAttribute("paging", paging);
        model.addAttribute("paginationBarNumbers", barNumbers);
        model.addAttribute("param", searchParam);
        return "view";
    }

    @GetMapping("/list")
    public String viewList(Model model){
        List<ArticleForm> dtoLists = articleService.viewList();
        model.addAttribute("dtoLists", dtoLists);
        return "view";
    }

    @GetMapping("/new")
    public String newArticleForm(Model model){
        model.addAttribute("dto", new ArticleForm());
        return "/articles/new";
    }

    @PostMapping("/create")
    public String newArticleSave(ArticleForm dto, Principal principal){
        Long id;
        id = articleService.articleSave(dto, principal);
        return "redirect:/articles/lists" ;
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model){
        Article article = articleService.getOneArticle(id);

        model.addAttribute("articleDto", new ArticleCommentDto());
        model.addAttribute("dto", article);
        return "articles/detail";
    }

    @GetMapping("/{id}/update")
    public String update(@PathVariable Long id, Model model){
        Article article = articleService.getOneArticle(id);
        model.addAttribute("dto", article);
        return "articles/update";
    }

    @PostMapping("/update")
    public String updateArticle(@RequestParam("id") Long id, ArticleForm dto){
        articleService.articleUpdate(dto, id);
        return "redirect:/articles/lists" ;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        articleService.deleteArticle(id);
        redirectAttributes.addFlashAttribute("msg", "삭제 되었습니다.");
        return "redirect:/articles/lists" ;
    }

    @PostMapping("/{id}/articleComment")
    public String insertArticleComment(@PathVariable Long id, ArticleCommentDto articleDto, Principal principal){
        articleService.articleCommentSave(id, articleDto, principal);
        return "redirect:/articles/" + id ;
    }

    @PostMapping("/{id}/articleComments/{article-comment-id}/delete")
    public String deleteArticleComment(@PathVariable("id") Long articleId,
                                       @PathVariable("article-comment-id") Long articleCommentId){
        articleService.articleCommentDelete(articleCommentId);
        return "redirect:/articles/" + articleId ;
    }

}
