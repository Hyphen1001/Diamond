package ghlzblog.api;

public class Result {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    private Object object;

    public static Result succ()
    {
        Result r = new Result();
        r.setCode(200);
        r.setMessage("登陆成功");
        r.setObject(null);
        return r;
    }
    public static Result fail()
    {
        Result r = new Result();
        r.setCode(400);
        r.setMessage("登陆失败");
        r.setObject(null);
        return r;
    }

    public static Result fail(int code,String message)
    {
        Result r = new Result();
        r.setCode(code);
        r.setMessage(message);
        r.setObject(null);
        return r;
    }

}
