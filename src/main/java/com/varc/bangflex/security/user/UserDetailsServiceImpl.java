package com.varc.bangflex.security.user;

import com.varc.bangflex.domain.user.entity.Member;
import com.varc.bangflex.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		Member user = userRepository.findById(id)
			.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + id));
		return new UserDetailsImpl(user);
	}
}