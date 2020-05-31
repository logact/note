package com.logact.cateen.controller.pageController;

import com.logact.cateen.service.page.SearchService;
import com.logact.cateen.vo.search.RelatedOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/19 16:10
 * @description:
 */
@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    SearchService searchService;
//    这里暂时实现一个只能搜索食品的。
    @RequestMapping("relatedOptions")
    public List<RelatedOption> getRelatedOptions(String word){
        List<RelatedOption> res= searchService.getRelatedOptions(word);
        return res;
    }

}
