import java.util.ArrayList;
import java.util.List;

/**
 * 4. 셔틀버스(난이도: 중)
카카오에서는 무료 셔틀버스를 운행하기 때문에 판교역에서 편하게 사무실로 올 수 있다. 카카오의 직원은 서로를 ‘크루’라고 부르는데, 아침마다 많은 크루들이 이 셔틀을 이용하여 출근한다.

이 문제에서는 편의를 위해 셔틀은 다음과 같은 규칙으로 운행한다고 가정하자.

셔틀은 09:00부터 총 n회 t분 간격으로 역에 도착하며, 하나의 셔틀에는 최대 m명의 승객이 탈 수 있다.
셔틀은 도착했을 때 도착한 순간에 대기열에 선 크루까지 포함해서 대기 순서대로 태우고 바로 출발한다. 예를 들어 09:00에 도착한 셔틀은 자리가 있다면 09:00에 줄을 선 크루도 탈 수 있다.
일찍 나와서 셔틀을 기다리는 것이 귀찮았던 콘은, 일주일간의 집요한 관찰 끝에 어떤 크루가 몇 시에 셔틀 대기열에 도착하는지 알아냈다. 콘이 셔틀을 타고 사무실로 갈 수 있는 도착 시각 중 제일 늦은 시각을 구하여라.

단, 콘은 게으르기 때문에 같은 시각에 도착한 크루 중 대기열에서 제일 뒤에 선다. 또한, 모든 크루는 잠을 자야 하므로 23:59에 집에 돌아간다. 따라서 어떤 크루도 다음날 셔틀을 타는 일은 없다.

입력 형식
셔틀 운행 횟수 n, 셔틀 운행 간격 t, 한 셔틀에 탈 수 있는 최대 크루 수 m, 크루가 대기열에 도착하는 시각을 모은 배열 timetable이 입력으로 주어진다.

0 ＜ n ≦ 10
0 ＜ t ≦ 60
0 ＜ m ≦ 45
timetable은 최소 길이 1이고 최대 길이 2000인 배열로, 하루 동안 크루가 대기열에 도착하는 시각이 HH:MM 형식으로 이루어져 있다.
크루의 도착 시각 HH:MM은 00:01에서 23:59 사이이다.
출력 형식
콘이 무사히 셔틀을 타고 사무실로 갈 수 있는 제일 늦은 도착 시각을 출력한다. 도착 시각은 HH:MM 형식이며, 00:00에서 23:59 사이의 값이 될 수 있다.
 *
 */
public class Exam4 {

	public static void main(String[] args) {

		// 셔틀 운행 횟수
		int n = 10;
		
		// 셔틀 운행 간격
		int t = 60;
		
		// 셔틀에 탈수 있는 최대 크루수
		int m = 45;
		
		// 크루 도착 시각
		String[] timeTable = {"23:59","23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59"};
		
		String answer = "";
		
		int startTime = timeToNum("09:00");
		int lastTime = startTime + t * (n - 1);
		
		List<Integer> crewTime = new ArrayList();
		for ( int i = 0 ; i < timeTable.length ; i++ ) {
			crewTime.add( timeToNum( timeTable[i] ) );
		}
		
		// 정렬
		for ( int i = 0 ; i < crewTime.size() - 1 ; i++ ) {
			for ( int j = i + 1 ; j < crewTime.size() ; j++ ) {
				if ( crewTime.get(i) > crewTime.get(j) ) {
					int temp = crewTime.get(i);
					crewTime.set(i, crewTime.get(j));
					crewTime.set(j, temp);
				}
			}
		}
		
		for ( int i = 0 ; i < n ; i++ ) {
			int shuttleTime = startTime + i * t;
			
			int takeNum = 0;
			for ( int time : crewTime ) {
				if ( shuttleTime >= time ) takeNum++;
			}
			
//			System.out.println(shuttleTime + ", " + takeNum + ", " + crewTime.size());
			
			if (i == n - 1) {
				if (takeNum < m) {
					answer = numToTime(lastTime);
				} else {
					answer = numToTime(crewTime.get(m - 1) - 1);
				}
			} else {
				if (takeNum > m) {
					for ( int j = 0 ; j < m ; j++ ) {
						crewTime.remove(0);
					}
				} else {
					for ( int j = 0 ; j < takeNum ; j++ ) {
						crewTime.remove(0);
					}
				}
			}
		}
		
		System.out.println("제일 늦게 탈수 있는 도착시각 : " + answer);
	}
	
	// 시각 -> 분
	private static int timeToNum(String time) {
		return Integer.parseInt( time.split("\\:")[0] ) * 60 + Integer.parseInt( time.split("\\:")[1] );
	}
	
	// 분 -> 시각
	private static String numToTime(int num) {
		int hour = num / 60;
		int minute = num % 60;
		return (hour < 10 ? "0" : "") + hour + ":" + (minute < 10 ? "0" : "") + minute;
	}
}
