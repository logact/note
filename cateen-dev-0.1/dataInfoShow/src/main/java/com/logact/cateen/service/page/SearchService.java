package com.logact.cateen.service.page;

import com.logact.cateen.vo.search.RelatedOption;

import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/19 16:17
 * @description:
 */

public interface SearchService {
     List<RelatedOption> getRelatedOptions(String word);
}
