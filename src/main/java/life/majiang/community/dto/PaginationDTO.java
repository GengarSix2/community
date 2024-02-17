package life.majiang.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO
{
    private List<QuestionDTO> questions; // 显示在当前页面上的问题
    private boolean showPrevious; // 是否显示跳转至上一页
    private boolean showFirstPage; // 是否显示跳转至第一页
    private boolean showNext; // 是否显示跳转至下一页
    private boolean showEndPage; // 是否显示跳转至最后一页
    private Integer page; // 当前页面序号
    private Integer totalPage; // 总页面数
    private List<Integer> pages = new ArrayList<>(); // 当前分页显示的页面数字

    public void setPagination(Integer totalPage, Integer page)
    {
        this.totalPage = totalPage;
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
        showPrevious = page != 1;
        // 是否展示下一页
        showNext = !page.equals(totalPage);
        // 是否展示第一页
        showFirstPage = !pages.contains(1);
        // 是否展示尾页
        showEndPage = !pages.contains(totalPage);
    }
}
