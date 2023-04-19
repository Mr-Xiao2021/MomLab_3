package domain;


/**
 * ClassName User
 * Description  TODO
 *
 * @author Mr_X
 * @version 1.0
 * @date 2023/4/18 17:12
 */

public class User {
    private String name;
    private String info;

    public User(String name,String info){
        this.name = name;
        this.info = info;
    }
    public User(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        return info != null ? info.equals(user.info) : user.info == null;
    }

    /**
     * 目前不支持同名用户
     * @return
     */
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (info != null ? info.hashCode() : 0);
        return result;
    }
}
