import styled from 'styled-components';

export function IconCheckDisabled({ rest }) {
	return (
		<StyledWrapper {...rest}>
			<svg
				width='16'
				height='16'
				viewBox='0 0 16 16'
				fill='none'
				xmlns='http://www.w3.org/2000/svg'
			>
				<rect
					x='0.5'
					y='0.5'
					width='15'
					height='15'
					rx='1.5'
					fill='currentColor'
					stroke='currentColor'
				/>
				<path
					d='M6 8H10'
					stroke='#FEFEFE'
					strokeWidth='1.6'
					strokeLinecap='round'
					strokeLinejoin='round'
				/>
			</svg>
		</StyledWrapper>
	);
}
const StyledWrapper = styled.i`
	padding: 0;
`;
