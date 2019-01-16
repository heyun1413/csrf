package ron.pub.csrf;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final String AUTHENTICATED = "AUTHENTICATED";


    private HttpSession session;

    @Autowired
    public void setSession(HttpSession session) {
        this.session = session;
    }

    private void authenticated() {
        session.setAttribute(AUTHENTICATED, Boolean.TRUE);
    }

    private boolean isAuthenticated() {
        Object attribute = session.getAttribute(AUTHENTICATED);
        if (attribute == null) {
            return false;
        }
        return (Boolean) attribute;
    }

    @PostMapping("/authc")
    @ApiOperation("登录认证")
    public ResponseEntity<String> authc(@ApiParam(required = true, defaultValue = "ron") @RequestParam String username,
                                        @ApiParam(required = true, defaultValue = "123") @RequestParam String password) {
        if (username.equals("ron") && password.equals("123")) {
            authenticated();
            return ResponseEntity.ok("登录成功");
        }
        return ResponseEntity.ok("用户名或密码错误");
    }

    @GetMapping("/withdraw")
    @ApiOperation("转账")
    public ResponseEntity<String> withdraw(@ApiParam(required = true, defaultValue = "100") @RequestParam int amount,
                                           @ApiParam(required = true, defaultValue = "friend") @RequestParam String who) {
        if (isAuthenticated()) {
            return ResponseEntity.ok(String.format("我转了%d给%s", amount, who));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("您没有登录");
    }


}
