package com.rson.rsoncoffeeshop.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="giftcards")
public class GiftCard {
	
	private static Integer currentCardNumber = 3598;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Min(value=1000)
	private Integer cardNumber;
	@Min(value=100)
	private Integer securityCode;
	@NotNull
	private Integer value;
	private Long userId;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="fakeuser_id")
	private FakeUser owner;
	
	{
		currentCardNumber++;
	}
	
	public GiftCard() {
		this.cardNumber = currentCardNumber;
	}
	
	public Integer getSecurityCode() {
		return securityCode;
	}
	
	public void setSecurityCode(Integer securityCode) {
		this.securityCode = securityCode;
	}

	public static Integer getCurrentCardNumber() {
		return currentCardNumber;
	}

	public static void setCurrentCardNumber(Integer currentCardNumber) {
		GiftCard.currentCardNumber = currentCardNumber;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(Integer cardNumber) {
		this.cardNumber = cardNumber;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public FakeUser getOwner() {
		return owner;
	}
	public void setOwner(FakeUser owner) {
		this.owner = owner;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	@Column(updatable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createdAt;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date updatedAt;

	@PrePersist
	protected void onCreate(){
	    this.createdAt = new Date();
	}
	@PreUpdate
	protected void onUpdate(){
	    this.updatedAt = new Date();
	}
}
