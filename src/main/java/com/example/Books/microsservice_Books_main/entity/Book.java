    package com.example.Books.microsservice_Books_main.entity;

    import com.example.Books.microsservice_Books_main.enums.ContentRatingEnum;
    import jakarta.persistence.*;

    import java.io.Serializable;
    import java.util.Objects;

    @Entity
    @Table(name = "tb_books")
    public class Book implements Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private String title;
        private String author;
        private int publishedYear;
        private String gender;
        private String pagesQuantity;

        @Enumerated(EnumType.STRING)
        @Column(name = "content_rating")
        private ContentRatingEnum contentRating;

        private String imagePath;

        public Book() {}

        public Book(long id, String title, String author, int publishedYear, String gender, String pagesQuantity, ContentRatingEnum contentRating, String imagePath) {
            super();
            this.id = id;
            this.title = title;
            this.author = author;
            this.publishedYear = publishedYear;
            this.gender = gender;
            this.pagesQuantity = pagesQuantity;
            this.contentRating = contentRating;
            this.imagePath = imagePath;
        }

        public ContentRatingEnum getContentRating() {return contentRating;}

        public void setContentRating(ContentRatingEnum contentRating) {this.contentRating = contentRating;}

        public String getPagesQuantity() {return pagesQuantity;}

        public void setPagesQuantity(String pagesQuantity) {this.pagesQuantity = pagesQuantity;}

        public long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getPublishedYear() {
            return publishedYear;
        }

        public void setPublishedYear(int PublishedYear) {this.publishedYear = PublishedYear;}

        public String getGender() {return gender;}

        public void setGender(String gender) {this.gender = gender;}

        public String getImagePath() {return imagePath;}

        public void setImagePath(String imagePath) {this.imagePath = imagePath;}

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Book book)) return false;
            return getId() == book.getId() && getPublishedYear() == book.getPublishedYear() && Objects.equals(getTitle(), book.getTitle())
                    && Objects.equals(getAuthor(), book.getAuthor()) && Objects.equals(getGender(), book.getGender()) && Objects.equals(getPagesQuantity()
                    , book.getPagesQuantity()) && getContentRating() == book.getContentRating() && Objects.equals(imagePath, book.imagePath);
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getTitle(), getAuthor(), getPublishedYear(), getGender(), getPagesQuantity(), getContentRating(), imagePath);
        }
    }