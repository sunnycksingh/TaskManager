package com.assignment.TaskMaster.service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.assignment.TaskMaster.dto.RegisterRequest;
import com.assignment.TaskMaster.dto.UserResponse;
import com.assignment.TaskMaster.entity.User;
import com.assignment.TaskMaster.repository.UserRepository;
import com.assignment.TaskMaster.dto.UpdateProfileRequest;

@Service 
public class UserService 
{
   
   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;

   public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder)
   {
      this.userRepository = userRepository;
      this.passwordEncoder = passwordEncoder;
   }

 
   // Register the User Method Logic

   public UserResponse registerUser(RegisterRequest request)
   {
      // Check if a user with this email already exists

      if(userRepository.existsByEmail(request.getEmail()))
      {
         throw new RuntimeException("Email ALready Exists");
      }

      // Create a new User Object

      User user = new User();

      // Copy username, email, phone from the request onto the user

      user.setUsername(request.getUsername());
      user.setEmail(request.getEmail());
      user.setPhone(request.getPhone());

      // Hash the password

      String hashedPassword = passwordEncoder.encode(request.getPassword());

      // Set the password into user object

      user.setPassword(hashedPassword);

      // Save the user values into DB

      User savedUser = userRepository.save(user);

      // Convert the saved User into a UserResponse

      //  UserResponse response = new UserResponse();

      //  response.setId(savedUser.getUserid());
      //  response.setUsername(savedUser.getUsername());
      //  response.setEmail(savedUser.getEmail());
      //  response.setPhone(savedUser.getPhone());

      //  return response;

      // Why we did that to remove the code reduntency 

      return mapToResponse(savedUser);

   }

   // Find User by Id Method Logic

   public UserResponse getUserById(Long id)
   {
      // Find User by Id

      User user = userRepository.findById(id).orElseThrow(() ->
   
      new RuntimeException("User not found with the given Id: "+ id)
   );
   
     // Convert to UserResponse and return

   //   UserResponse response = new UserResponse();
   //   response.setId(user.getUserid());
   //   response.setUsername(user.getUsername());
   //   response.setEmail(user.getEmail());
   //   response.setPhone(user.getPhone());

     // Return the response

   //   return response;

   // Why we did that to remove the code reduntency 

      return mapToResponse(user);

   }


   // Update Profile Method Logic

  public UserResponse updateProfile(Long id, UpdateProfileRequest request)
  {
     // Find the User By Id 

     User user = userRepository.findById(id).orElseThrow(() ->

      new  RuntimeException("The given User not found: "+id)

   );

    // Set the Username and Phone from the request

    user.setUsername(request.getUsername());
    user.setPhone(request.getPhone());

    // Save the User after Updation 

    User updatedUser = userRepository.save(user);

    // Convert to UserResponse and return 

   //  UserResponse response = new UserResponse();
   //  response.setId(updatedUser.getUserid());
   //  response.setUsername(updatedUser.getUsername());
   //  response.setEmail(updatedUser.getEmail());
   //  response.setPhone(updatedUser.getPhone());

   //  return response;

   // Why we did that to remove the code reduntency 

      return mapToResponse(updatedUser);

  }

  // Helper method: mapToResponse

  private  UserResponse mapToResponse(User user)
  {
     // Create a UserResponse

     UserResponse response = new UserResponse();
     response.setId(user.getUserid());
     response.setUsername(user.getUsername());
     response.setEmail(user.getEmail());
     response.setPhone(user.getPhone());
     response.setCreatedAt(user.getCreatedAt());

     return response;

  }
}
