DELIMITER //
CREATE PROCEDURE executeSeckill(IN fadeSeckillId INT,IN fadeUserPhone VARCHAR (15),IN fadeKillTime TIMESTAMP ,OUT fadeResult INT)
  BEGIN
    DECLARE insertCount INT DEFAULT 0;
    START TRANSACTION ;
    INSERT IGNORE success_killed(seckill_id,user_phone,state,create_time) VALUES(fadeSeckillId,fadeUserPhone,0,fadeKillTime);  --�Ȳ��빺����ϸ
    SELECT ROW_COUNT() INTO insertCount;
    IF(insertCount = 0) THEN
      ROLLBACK ;
      SET fadeResult = -1;   --�ظ���ɱ
    ELSEIF(insertCount < 0) THEN
      ROLLBACK ;
      SET fadeResult = -2;   --�ڲ�����
    ELSE   --�Ѿ����빺����ϸ��������Ҫ���ٿ��
      UPDATE seckill SET number = number -1 WHERE seckill_id = fadeSeckillId AND start_time < fadeKillTime AND end_time > fadeKillTime AND number > 0;
      SELECT ROW_COUNT() INTO insertCount;
      IF (insertCount = 0)  THEN
        ROLLBACK ;
        SET fadeResult = 0;   --���û���ˣ�������ɱ�Ѿ��ر�
      ELSEIF (insertCount < 0) THEN
        ROLLBACK ;
        SET fadeResult = -2;   --�ڲ�����
      ELSE
        COMMIT ;    --��ɱ�ɹ��������ύ
        SET  fadeResult = 1;   --��ɱ�ɹ�����ֵΪ1
      END IF;
    END IF;
  END
//

DELIMITER ;

SET @fadeResult = -3;
CALL executeSeckill(8,11111111111,NOW(),@fadeResult);
SELECT @fadeResult;
