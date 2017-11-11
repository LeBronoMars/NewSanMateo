package sanmateo.com.profileapp.framework;

/**
 * Created by rsbulanon on 11/11/2017.
 */

public class MockProfileApplication extends ProfileApplication {

    MockProfileComponent mockProfileComponent;

    @Override
    ProfileComponent initializeComponent() {
        mockProfileComponent = DaggerMockProfileComponent.builder().application(this).build();
        return mockProfileComponent;
    }

    public  MockProfileComponent getComponent () {
        return mockProfileComponent;
    }
}
