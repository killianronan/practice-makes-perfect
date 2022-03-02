
public class StringSearchAlgorithms {
	public static int bruteForce(String c, String cto) {
        int m = c.length();
        int n = cto.length();		
        for(int i = 0; i<=n-m; i++) {
        	int j;
			for(j = 0; j<m; j++) {
				if(cto.charAt(i+j)!=c.charAt(j)){
					break;
				}
			}
			if(j==m) return i; //return starting index of found substring found
		}
		return n; //not found
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String pattern = "NEEDLE";
		String text = "INAHAYSTACKNEEDLEINA";
		int x =bruteForce(pattern, text);
		System.out.println(x);

	}

}
