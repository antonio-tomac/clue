package mt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.LegacyNumericRangeQuery;
import org.apache.lucene.search.Query;

/**
 *
 * @author Antonio Tomac <antonio.tomac@mediatoolkit.com>
 */
public class NumericParser extends QueryParser {

	private static final Pattern PATTERN = Pattern.compile("(\\w+):\\[(\\d+) TO (\\d+)\\]");

	public NumericParser(String f, Analyzer a) {
		super(f, a);
		System.out.println("ja konstruiran");
	}

	@Override
	public Query parse(String query) throws ParseException {
		boolean constant = query.startsWith("const ");
		if (constant) {
			query = query.substring("const ".length());
		}
		System.out.println("ja idem parsat: " + query);
		Matcher matcher = PATTERN.matcher(query);
		if (matcher.find()) {
			String fName = matcher.group(1);
			String from = matcher.group(2);
			String to = matcher.group(3);
			LegacyNumericRangeQuery<Long> newLongRange = LegacyNumericRangeQuery.newLongRange(
				fName, Long.parseLong(from), Long.parseLong(to), true, true
			);
			System.out.println("ja napravio kveri: " + newLongRange);
			return newLongRange;
		}
		Query parsedQuery = super.parse(query);
		Query finalQuery;
		if (constant) {
			finalQuery = new BooleanQuery.Builder().add(parsedQuery, Occur.FILTER).build();
		} else {
			finalQuery = parsedQuery;
		}
		System.out.println("final query: " + finalQuery);
		return finalQuery;
	}

	public static void main(String[] args) throws ParseException {
		new NumericParser("x", new StandardAnalyzer()).parse("");
	}

}
