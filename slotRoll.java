
public class slotRoll {
	
	//圖示：7、BAR、櫻桃、鑽石、檸檬、蔔萄
	public static final int CHERRY =1;
	public static final int LEMON =4;
	public static final int GRAPE =15;
	public static final int DIAMOND =50;
	public static final int BAR =200;
	public static final int SEVEN =1000;
	//1-16為有效組 ，0和17為頭尾相接，為虛組用來顯示用；0=16，17=1;
	private int[] roll = new int[18];
	

	//建構列表
	slotRoll(int kind) {
		
		if(kind<=0 || kind%3 ==0) {
			kind = 1;
		}else if(kind %3 ==1) {
			kind = 2;
		}else if(kind %3 ==2){
			kind = 3;	
		}
		
		// 水果3-4個、鑽石、bar 3個、777 一個
		switch(kind) {
		case 1:
			//普通式
			roll[0] = LEMON ;
			roll[1] = SEVEN ;
			roll[2] = BAR ;
			roll[3] = GRAPE ;
			roll[4] = DIAMOND ;
			roll[5] = BAR ;
			roll[6] = CHERRY ;
			roll[7] =  DIAMOND;
			roll[8] = BAR ;
			roll[9] = CHERRY ;
			roll[10] = BAR;
			roll[11] = DIAMOND ;
			roll[12] = CHERRY ;
			roll[13] = GRAPE ;
			roll[14] = CHERRY ;
			roll[15] = DIAMOND ;
			roll[16] = LEMON ;
			roll[17] = SEVEN ;	
			break;
			
		case 2:
			//倒序式
			roll[17] = SEVEN ;
			roll[16] = BAR ;
			roll[15] = CHERRY ;
			roll[14] = DIAMOND ;
			roll[13] = CHERRY ;
			roll[12] = BAR ;
			roll[11] = DIAMOND ;
			roll[10] = GRAPE ;
			roll[9] = LEMON ;
			roll[8] = CHERRY ;
			roll[7] = BAR;
			roll[6] = DIAMOND ;
			roll[5] = BAR ;
			roll[4] = GRAPE ;
			roll[3] = DIAMOND ;
			roll[2] = CHERRY ;
			roll[1] = SEVEN ;
			roll[0] = BAR ;
			break;		
		case 3:
			//交錯式
			roll[0] = LEMON ;
			roll[1] = SEVEN ;
			roll[15] = DIAMOND ;
			roll[3] =  BAR;
			roll[13] = CHERRY ;
			roll[5] = BAR ;
			roll[11] = CHERRY;
			roll[7] = DIAMOND ;
			roll[9] = CHERRY ;
			roll[8] = GRAPE ;
			roll[10] = BAR ;
			roll[6] =  DIAMOND;
			roll[12] = BAR ;
			roll[4] = GRAPE ;
			roll[14] = CHERRY ;
			roll[2] = DIAMOND ;
			roll[16] = LEMON ;
			roll[17] = SEVEN ;
			break;
		}
		
	}
	//賠率計算
	public static int odds(int roll1,int roll2,int roll3) {
		int odds = 0;
		int score =roll1 + roll2 +roll3;
		
		switch(score) {
		case 3: // 櫻桃 櫻桃 櫻桃
			odds = 5;
			break;
		case 12: // 檸檬 檸檬 檸檬
			odds = 40;
			break;
		case 21: //隨機三種水果
			odds = 10;
			break;
		case 45: //葡萄 葡萄 葡萄
			odds = 40;
			break;
	
		case 101: //鑽 鑽 櫻桃
			if (roll3 == 1) {
				odds = 2;
			}
			break;			
		case 150: //鑽 鑽 鑽
			odds = 100;
			break;
		case 401: //bar bar 櫻桃
			if (roll3 == 1) {
				odds = 2;
			}
			break;	
		case 600: //bar bar bar
			odds = 200;
			break;	
		case 2001://7、7、櫻桃
			if (roll3 == 1) {
				odds = 2;
			}
			break;
		case 3000: // 7 7 7
			odds = 777;
			break;	
		}
		return odds;
		
	}

	public int[] getRoll() {
		return roll;
	}
	public void setRoll(int[] roll) {
		this.roll = roll;
	}
	
//end
}
