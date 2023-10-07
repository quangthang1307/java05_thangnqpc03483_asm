package poly.edu.service;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;


@Service
public class ParamService {
    @Autowired
    HttpServletRequest request;

    /**
     * Đọc chuỗi giá trị của tham số
     * @param name          tên tham số
     * @param defaultValue  giá trị mặc định
     * @return giá trị tham số hoặc giá trị mặc định nếu không tồn tại
     */
    public String getString(String name, String defaultValue) {
        String paramValue = request.getParameter(name);
        return (paramValue != null) ? paramValue : defaultValue;
    }

    /**
     * Đọc số nguyên giá trị của tham số
     * @param name          tên tham số
     * @param defaultValue  giá trị mặc định
     * @return giá trị tham số hoặc giá trị mặc định nếu không tồn tại
     */
    public int getInt(String name, int defaultValue) {
        String paramValue = request.getParameter(name);
        try {
            return (paramValue != null) ? Integer.parseInt(paramValue) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Đọc số thực giá trị của tham số
     * @param name          tên tham số
     * @param defaultValue  giá trị mặc định
     * @return giá trị tham số hoặc giá trị mặc định nếu không tồn tại
     */
    public double getDouble(String name, double defaultValue) {
        String paramValue = request.getParameter(name);
        try {
            return (paramValue != null) ? Double.parseDouble(paramValue) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Đọc giá trị boolean của tham số
     * @param name          tên tham số
     * @param defaultValue  giá trị mặc định
     * @return giá trị tham số hoặc giá trị mặc định nếu không tồn tại
     */
    public boolean getBoolean(String name, boolean defaultValue) {
        String paramValue = request.getParameter(name);
        return (paramValue != null) ? Boolean.parseBoolean(paramValue) : defaultValue;
    }

    /**
     * Đọc giá trị thời gian của tham số
     * @param name    tên tham số
     * @param pattern là định dạng thời gian
     * @return giá trị tham số hoặc null nếu không tồn tại
     * @throws RuntimeException lỗi sai định dạng
     */
    public Date getDate(String name, String pattern) throws RuntimeException {
        String paramValue = request.getParameter(name);
        // Sử dụng pattern để chuyển đổi paramValue thành đối tượng Date
        // Ví dụ: SimpleDateFormat để chuyển đổi từ chuỗi thành Date
        // Xử lý lỗi nếu có sai định dạng
        return null; // Trả về null nếu không tồn tại hoặc lỗi sai định dạng
    }

    /**
     * Lưu file upload vào thư mục
     * @param file chứa file upload từ client
     * @param path đường dẫn tính từ webroot
     * @return đối tượng chứa file đã lưu hoặc null nếu không có file upload
     * @throws RuntimeException lỗi lưu file
     */
    @Autowired
    ServletContext application;
    public File save(MultipartFile file, String path) throws RuntimeException {
        try {
            // Thay đổi đường dẫn lưu trữ hình ảnh
            File uploadDirectory = new File(application.getRealPath(path));
            System.out.println();

            // Đảm bảo thư mục upload đã tồn tại, nếu không thì tạo mới
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }

            // Lưu file vào thư mục upload
            File savedFile = new File(uploadDirectory, file.getOriginalFilename());
            file.transferTo(savedFile);
            String tn = application.getRealPath(path);            
            return savedFile;
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException("Failed to save file: " + e.getMessage());
        }
    }

}

