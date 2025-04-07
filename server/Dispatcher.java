package server;

import server.annnotation.GetRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Arrays;
import java.util.Optional;

public class Dispatcher implements Runnable {

    private Socket socket;

    public Dispatcher(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = bufferedReader.readLine();
            String[] request = line.split(" ");
            String httpMethod = request[0];
            String endpoint = request[1];
            Optional<Method> endpointOptional = Arrays.stream(Controller.class.getDeclaredMethods())
                    .filter((m) -> m.isAnnotationPresent(GetRequest.class))
                    .filter((m) -> m.getDeclaredAnnotation(GetRequest.class).endpoint().equals(endpoint))
                    .findFirst();
            if(endpointOptional.isEmpty()) {
                throw new RuntimeException("Endpoint not found");
            }
            Method method = endpointOptional.get();
            method.invoke(new Controller());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
