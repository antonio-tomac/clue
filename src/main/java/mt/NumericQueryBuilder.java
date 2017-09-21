package mt;


import com.senseidb.clue.api.QueryBuilder;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;


/**
 *
 * @author Antonio Tomac <antonio.tomac@mediatoolkit.com>
 */
public class NumericQueryBuilder implements QueryBuilder {

	QueryParser parser = null;
	
	@Override
	public void initialize(String defaultField, Analyzer analyzer) throws Exception {
		parser = new NumericParser(defaultField, analyzer);
	}

	@Override
	public Query build(String rawQuery) throws Exception {
		return parser.parse(rawQuery);
	}
	
}
