package unit_test.search;

import java.util.ArrayList;

public class ResponseSearch {
    String code;
    String message;
    Data data;

}
class Data{
    ArrayList<ListPosts> list_posts = new ArrayList<ListPosts>();
}

class  ListPosts{
    String id;
    String image;
    Video video;
    String like;
    String comment;
    String is_like;
    Author author;
    String described;
}

class Video{
    String thumb;
    String url;
}

class Author{
    String id;
    String username;
    String avatar;
}