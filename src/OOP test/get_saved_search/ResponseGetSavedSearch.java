package unit_test.get_saved_search;

import java.util.ArrayList;
import unit_test.get_saved_search.Data;
import unit_test.get_saved_search.ListSavedSearches;

public class ResponseGetSavedSearch {
    String code;
    String message;
    Data data;
}

class Data{
    ArrayList<ListSavedSearches> list_saved_search = new ArrayList<ListSavedSearches>();
}

class ListSavedSearches{
    String id;
    String keyword;
    String created;
}