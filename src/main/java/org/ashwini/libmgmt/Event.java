package org.ashwini.libmgmt;

public class Event {
    private String httpMethod;
    private BookItem book;
    private Query query;

    public String getHttpMethod() {
        return httpMethod;
    }
    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }


    public BookItem getBook() {
        return book;
    }
    public void setBook(BookItem book) {
        this.book = book;
    }


    public Query getQuery() {
        return query;
    }
    public void setQuery(Query query) {
        this.query = query;
    }
}
