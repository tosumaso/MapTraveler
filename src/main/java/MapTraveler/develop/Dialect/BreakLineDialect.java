package MapTraveler.develop.Dialect;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

public class BreakLineDialect extends AbstractProcessorDialect{

	private static final String NAME = "break line dialect";
	private static final String PREFIX = "bld";
	
	public BreakLineDialect() {
		super(NAME, PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		Set<IProcessor> processors = new HashSet<>();
		processors.add(new BreakLineProcessor(dialectPrefix,getDialectProcessorPrecedence()));
		return processors;
	}
}
