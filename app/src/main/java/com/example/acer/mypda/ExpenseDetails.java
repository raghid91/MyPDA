package com.example.acer.mypda;

/**
 * Created by Raghid Nami - ID: 260701283
 * For the CCCS325 - Mobile Development Course
 * Assignment 3
 */

public class ExpenseDetails {
    private String reference;
    private String description;
    private String category;
    private String date;
    private String amount;
    private int _id;
    private int is_done;

    public ExpenseDetails(){

    }

    public ExpenseDetails(String date, String reference, String description, String category, String amount, int done){
        this.setReference(reference);
        this.setDescription(description);
        this.setCategory(category);
        this.setDate(date);
        this.setAmount(amount);
        this.setIs_done(done);
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String toString(){
        return ("" + category + "\t | Ref: " + reference + "\n" + description + "\t $" + amount + "\t | Date: " + date);
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getIs_done() {
        return is_done;
    }

    public void setIs_done(int is_done) {
        this.is_done = is_done;
    }
}
