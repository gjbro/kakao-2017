import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 7. 추석 트래픽(난이도: 상)
이번 추석에도 시스템 장애가 없는 명절을 보내고 싶은 어피치는 서버를 증설해야 할지 고민이다. 장애 대비용 서버 증설 여부를 결정하기 위해 작년 추석 기간인 9월 15일 로그 데이터를 분석한 후 초당 최대 처리량을 계산해보기로 했다. 초당 최대 처리량은 요청의 응답 완료 여부에 관계없이 임의 시간부터 1초(=1,000밀리초)간 처리하는 요청의 최대 개수를 의미한다.

입력 형식
solution 함수에 전달되는 lines 배열은 N(1 ≦ N ≦ 2,000)개의 로그 문자열로 되어 있으며, 각 로그 문자열마다 요청에 대한 응답완료시간 S와 처리시간 T가 공백으로 구분되어 있다.
응답완료시간 S는 작년 추석인 2016년 9월 15일만 포함하여 고정 길이 2016-09-15 hh:mm:ss.sss 형식으로 되어 있다.
처리시간 T는 0.1s, 0.312s, 2s 와 같이 최대 소수점 셋째 자리까지 기록하며 뒤에는 초 단위를 의미하는 s로 끝난다.
예를 들어, 로그 문자열 2016-09-15 03:10:33.020 0.011s은 "2016년 9월 15일 오전 3시 10분 33.010초"부터 "2016년 9월 15일 오전 3시 10분 33.020초"까지 "0.011초" 동안 처리된 요청을 의미한다. (처리시간은 시작시간과 끝시간을 포함)
서버에는 타임아웃이 3초로 적용되어 있기 때문에 처리시간은 0.001 ≦ T ≦ 3.000이다.
lines 배열은 응답완료시간 S를 기준으로 오름차순 정렬되어 있다.
출력 형식
solution 함수에서는 로그 데이터 lines 배열에 대해 초당 최대 처리량을 리턴한다.
 *
 */
public class Exam7 {

	public static void main(String[] args) {
		final String[] lines = {"2016-09-15 20:59:57.421 0.351s", "2016-09-15 20:59:58.233 1.181s", "2016-09-15 20:59:58.299 0.8s", "2016-09-15 20:59:58.688 1.041s", "2016-09-15 20:59:59.591 1.412s", "2016-09-15 21:00:00.464 1.466s", "2016-09-15 21:00:00.741 1.581s", "2016-09-15 21:00:00.748 2.31s", "2016-09-15 21:00:00.966 0.381s", "2016-09-15 21:00:02.066 2.62s"};
		
		List<String> startTimes = new ArrayList();
		List<String> endTimes = new ArrayList();
		
		String minStartTime = "23:59:59.999";
		String maxEndTime = "00:00:00.000";
		
		for ( String line : lines ) {
			String endTime = line.split(" ")[1];
			String exeTime = line.split(" ")[2];
			int hour = Integer.parseInt( endTime.split("\\:")[0] );
			int minute = Integer.parseInt( endTime.split("\\:")[1] );
			double second = Double.parseDouble( endTime.split("\\:")[2] );
			
			double newSecond = second - ( Double.parseDouble( exeTime.replace("s", "") ) - 0.001 );
			if ( newSecond < 0 ) {
				if ( minute == 0 ) {
					minute = 59;
					hour--;
				} else {
					minute--;
				}
				
				second = newSecond + 60;
			} else {
				second = newSecond;
			}
			
			String startTime = (hour < 10 ? "0" : "") + hour + ":" + (minute < 10 ? "0" : "") + minute + ":" + (second < 10 ? "0" : "") + second;
			
			startTimes.add(startTime);
			endTimes.add(endTime);
			
			if ( startTime.compareTo(minStartTime) < 0 ) minStartTime = startTime;
			if ( endTime.compareTo(maxEndTime) > 0 ) maxEndTime = endTime;
		}
		
		System.out.println(startTimes);
		System.out.println(endTimes);
		
		System.out.println(minStartTime);
		System.out.println(maxEndTime);
		
		int maxAmount = 0;
		for ( String time = minStartTime ; time.compareTo( maxEndTime ) <= 0 ; ) {
			
//			System.out.println(time);
			
			int hour = Integer.parseInt( time.split("\\:")[0] );
			int minute = Integer.parseInt( time.split("\\:")[1] );
			double second = Double.parseDouble( time.split("\\:")[2] );
			
			second++;
			if ( second >= 60 ) {
				second = second - 1;
				minute++;
			}
			if ( minute == 60 ) {
				minute = 0;
				hour++;
			}
			
			String endTime = (hour < 10 ? "0" : "") + hour + ":" + (minute < 10 ? "0" : "") + minute + ":" + (second < 10 ? "0" : "") + second;
			
			int amount = 0;
			for ( int i = 0 ; i < startTimes.size() ; i++ ) {
				if ( time.compareTo(endTimes.get(i)) < 0 && endTime.compareTo(startTimes.get(i)) > 0 ) {
					amount++;
				}
			}
			if ( amount != 0 ) System.out.println(time + " > " + amount);
			if ( amount > maxAmount ) maxAmount = amount;
			
			hour = Integer.parseInt( time.split("\\:")[0] );
			minute = Integer.parseInt( time.split("\\:")[1] );
			second = Double.parseDouble( time.split("\\:")[2] );
			
			second = new BigDecimal( String.valueOf( second ) ).add( new BigDecimal( "0.001" ) ).doubleValue();
			if ( second >= 60 ) {
				second = second - 1;
				minute++;
			}
			if ( minute == 60 ) {
				minute = 0;
				hour++;
			}
			
			time = (hour < 10 ? "0" : "") + hour + ":" + (minute < 10 ? "0" : "") + minute + ":" + (second < 10 ? "0" : "") + second;
		}
		
		System.out.println("최대 처리량 : " + maxAmount);
	}

}
