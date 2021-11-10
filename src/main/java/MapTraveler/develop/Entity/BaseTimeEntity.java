package MapTraveler.develop.Entity;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass //このアノテーションをつけたentityを継承するとフィールドも継承される
@EntityListeners(AuditingEntityListener.class) // Auditing機能を追加
public abstract class BaseTimeEntity { //日時を自動登録したいentityに継承させる
	
	@CreatedDate //レコード登録時に日時を登録
	private LocalDateTime createdDate;

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
//	@LastModifiedDate	レコード更新時に日時を登録
//    private LocalDateTime modifiedDate;

	
}
