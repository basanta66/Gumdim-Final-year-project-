package com.aoh.ghumdim.shared;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
//@RequiredArgsConstructor
public class CommonConfiguration {
//    @Bean
//    public BM25 bm25(){
//
//        return  new BM25Okapi();
////        return new BM25Okapi(corpus, tokenizer, k1, b, epsilon);
//    }

//    @Bean
//    public void firebaseConfiguration() throws IOException {
//    ClassLoader classLoader = GhumdimApplication.class.getClassLoader();
//    File file = new File(Objects.requireNonNull(classLoader.getResource("serviceAccountKey.json")).getFile());
//    FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .build();
//        FirebaseApp.initializeApp(options );
//    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Bean
    public JavaMailSender mailSender(){
        return new JavaMailSenderImpl();
    }

}
