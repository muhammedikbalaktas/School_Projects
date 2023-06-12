package com.example.librarymanagement.classes;


import java.util.ArrayList;

public class BookManager {
    public void addBook(Book book){
        FileIO fileIO=new FileIO();
        fileIO.appendFile(book.getName(),"bookname.txt");
        fileIO.appendFile(book.getKind(),"bookkind.txt");
        fileIO.appendFile(book.getAuthor(),"bookauthor.txt");
        fileIO.appendFile(book.getPageCount(),"pagecount.txt");
        fileIO.appendFile(book.getPublishDate(),"publishdate.txt");

    }
    public ArrayList<String> getAllBooks(){
        FileIO fileIO=new FileIO();
        ArrayList <String> result=new ArrayList<>();
        ArrayList<String> names=fileIO.getData("bookname.txt");
        ArrayList<String> kinds=fileIO.getData("bookkind.txt");
        ArrayList<String> authors=fileIO.getData("bookauthor.txt");
        ArrayList<String> counts=fileIO.getData("pagecount.txt");
        ArrayList<String> dates=fileIO.getData("publishdate.txt");
        for (int i = 0; i < names.size(); i++) {
            String name=names.get(i);
            String kind=kinds.get(i);
            String author=authors.get(i);
            String count=counts.get(i);
            String date=dates.get(i);
            Book book=new Book(name,kind,author,count,date);
            result.add(book.toString());

        }
        return result;
    }

    public void deleteBook(String bookName){
        FileIO fileIO=new FileIO();
        ArrayList<String> names=fileIO.getData("bookname.txt");
        ArrayList<String> kinds=fileIO.getData("bookkind.txt");
        ArrayList<String> authors=fileIO.getData("bookauthor.txt");
        ArrayList<String> counts=fileIO.getData("pagecount.txt");
        ArrayList<String> dates=fileIO.getData("publishdate.txt");
        boolean isValid=false;
        for (int i = 0; i < names.size(); i++) {
            if (bookName.equals(names.get(i))){
                names.remove(i);
                kinds.remove(i);
                authors.remove(i);
                counts.remove(i);
                dates.remove(i);
                isValid=true;
                break;
            }
        }
        if (!isValid) System.out.println("There is no book with such name");
        fileIO.writeToFile(names,"bookname.txt");
        fileIO.writeToFile(kinds,"bookkind.txt");
        fileIO.writeToFile(authors,"bookauthor.txt");
        fileIO.writeToFile(counts,"pagecount.txt");
        fileIO.writeToFile(dates,"publishdate.txt");



    }

}