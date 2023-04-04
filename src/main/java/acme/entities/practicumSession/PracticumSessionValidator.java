
package acme.entities.practicumSession;

import java.util.Calendar;
import java.util.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import acme.framework.helpers.MomentHelper;

public class PracticumSessionValidator implements Validator {

	public static final int ONE_WEEK = 1;


	@Override
	public boolean supports(final Class<?> clazz) {
		return PracticumSession.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(final Object target, final Errors errors) {
		final PracticumSession session = (PracticumSession) target;
		final Date start = session.getStartPeriod();
		final Date end = session.getEndPeriod();
		final Date now = MomentHelper.getCurrentMoment();

		final Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.WEEK_OF_YEAR, PracticumSessionValidator.ONE_WEEK);
		final Date inAWeekFromNow = cal.getTime();

		cal.setTime(start);
		cal.add(Calendar.WEEK_OF_YEAR, PracticumSessionValidator.ONE_WEEK);
		final Date inAWeekFromStart = cal.getTime();

		if (MomentHelper.isBefore(start, inAWeekFromNow))
			errors.rejectValue("start", "start.beforeNow", "Start date must be after the current date");
		else if (MomentHelper.isBefore(end, inAWeekFromStart))
			errors.rejectValue("end", "end.beforeStart", "End date must be at least one week after the start date");
	}
}
