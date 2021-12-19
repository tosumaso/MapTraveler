//package MapTraveler.develop.Controller;
//
//import java.io.FileNotFoundException;
//
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice //全てのControllerで発生した例外をハンドリング
//public class ExceptionHandle {
//
//	@ExceptionHandler(NullPointerException.class) //ぬるぽのハンドリング
//	public String nullError(NullPointerException exception, Model model) {
//		model.addAttribute("errorMsg", exception.getMessage());
//		return "error_null";
//	}
//	
//	@ExceptionHandler(FileNotFoundException.class) //FileNotFoundExceptionのハンドリング
//	public String nofileError(FileNotFoundException exception, Model model) {
//		model.addAttribute("errorMsg", exception.getMessage());
//		return "error_no_file";
//	}
//	
//	@ExceptionHandler(Exception.class) //その他の例外をハンドリング
//	public String occurOtherException(Exception exception) {
//		return "errorMsg";
//	}
//}
