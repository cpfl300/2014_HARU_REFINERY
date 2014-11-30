package refinery.config;

import java.util.regex.Pattern;

import org.springframework.core.type.filter.RegexPatternTypeFilter;

public class RefineryPackageFilter extends RegexPatternTypeFilter {

	public RefineryPackageFilter() {
		super(Pattern.compile("refinery\\.scheduler\\.*"));
	}

}
