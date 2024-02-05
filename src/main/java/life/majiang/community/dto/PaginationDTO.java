package life.majiang.community.dto;

import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO
{
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page; // 当前页面序号
    private Integer totalPage; // 总页面数
    private List<Integer> pages = new ArrayList<>();

    public void setPagination()
    {
        pages.add(page);

        for (int i = 1; i <= 3; i++)
        {
            if (page - i > 0)
            {
                pages.add(0,page - i);
            }

            if (page + i <= totalPage)
            {
                pages.add(page+i);
            }
        }

        // 是否展示上一页
        if (page == 1)
        {
            showPrevious = false;
        } else
        {
            showPrevious = true;
        }

        // 是否展示下一页
        if (page.equals(totalPage))
        {
            showNext = false;
        } else
        {
            showNext = true;
        }

        // 是否展示第一页
        if (pages.contains(1))
        {
            showFirstPage = false;
        } else
        {
            showFirstPage = true;
        }

        // 是否展示尾页
        if (pages.contains(totalPage))
        {
            showEndPage = false;
        } else
        {
            showEndPage = true;
        }
    }
}
