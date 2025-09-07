package model;

public class Books {
	private int id;
	private String title;
	private String author;
	private int price;
	private String imagePath;
	
	//Ham khoi tao khongtham so
	public Books() {
		
	}
	
	//Ham khoi tao co tham so
	public Books (int id, String title, String author, int price, String imagePath) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.price = price;
		this.imagePath = imagePath;
	}
	
	//Get Set
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
}
