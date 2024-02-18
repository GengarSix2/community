package life.majiang.community.service;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService
{
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size)
    {
        int totalCount = questionMapper.count();
        int totalPage;
        if (totalCount % size == 0)
        {
            totalPage = totalCount / size;
        } else
        {
            totalPage = totalCount / size + 1;
        }

        // 处理当前页序号不合法的情况
        if (page < 1)
        {
            page = 1;
        }
        if (page > totalPage)
        {
            page = totalPage;
        }
        Integer offset = size * (page - 1);

        List<Question> questionList = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList)
        {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setQuestions(questionDTOList);
        paginationDTO.setPage(page);
        paginationDTO.setTotalPage(totalPage);
        paginationDTO.setPagination(totalPage, page);

        return paginationDTO;
    }

    public PaginationDTO listByUserID(Integer userID, Integer page, Integer size)
    {
        Integer totalCount = questionMapper.countByUserId(userID);
        int totalPage;

        if (totalCount % size == 0)
        {
            totalPage = totalCount / size;
        } else
        {
            totalPage = totalCount / size + 1;
        }

        // 处理当前页序号不合法的情况
        if (page < 1)
        {
            page = 1;
        }
        if (page > totalPage)
        {
            page = totalPage;
        }
        Integer offset = size * (page - 1);

        List<Question> questionList = questionMapper.listByUserID(userID, offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList)
        {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setQuestions(questionDTOList);
        paginationDTO.setPage(page);
        paginationDTO.setTotalPage(totalPage);
        paginationDTO.setPagination(totalPage, page);

        return paginationDTO;
    }

    public QuestionDTO getById(Integer questionId)
    {
        Question question = questionMapper.getById(questionId);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);

        return questionDTO;
    }

    public void createOrUpdate(Question question)
    {
        if (question.getId() == null)
        {
            // 创建问题
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        } else
        {
            // 更新问题
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }
}
