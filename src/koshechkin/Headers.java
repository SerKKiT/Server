package koshechkin;

enum Headers {
	Content_Length,
	Content_Type,
	Last_Modified;
	public String get() {
		return this.toString().replace("_", "-");
	} 
}
