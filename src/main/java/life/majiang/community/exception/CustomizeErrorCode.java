package life.majiang.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode
{
    QUESTION_NOT_FOUND("问题不存在，要不要换个试试？");
    private String message;

    CustomizeErrorCode(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
