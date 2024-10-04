import type { Config } from 'tailwindcss'

const config: Config = {
  mode: 'jit',
  content: [
    './src/pages/**/*.{js,ts,jsx,tsx,mdx}',
    './src/components/**/*.{js,ts,jsx,tsx,mdx}',
    './src/app/**/*.{js,ts,jsx,tsx,mdx}',
    './src/containers/**/*.{js,ts,jsx,tsx,mdx}',
    './src/hooks/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  theme: {
    extend: {
      colors: {
        'primary-50': '#FFE4E9',
        'primary-100': '#FFC9D3',
        'primary-200': '#FFAEBD',
        'primary-300': '#FF93A7',
        'primary-400': '#FF6A85',
        'primary-500': '#FF4365',
        'primary-600': '#DA3956',
        'primary-700': '#B62F48',
        'primary-800': '#912639',
        'primary-900': '#6D1C2B',
        'primary-950': '#48131C',
        'secondary': '#FFECC7',
        'secondary-medium': '#FFFBF3',
        'secondary-light': '#FFFEFC',
        'text': '#240F0F',
        'gray-dark': '#A8A8A8',
        'gray-medium': '#EDEBEC',
        'gray-light': '#FFFCFD',
      },
      fontFamily: {
        pretendard: ['Pretendard', 'Arial', 'sans-serif'],
      },
      height: {
        tabbar: '4rem',
      },
      backgroundImage: {
        'navbar-all-gradient':
          'linear-gradient(180deg, #FFFBF3 0%, #FFF 28.5%)',
      },
      keyframes: {
        'short-slide-up': {
          '0%': { transform: 'translateY(0%)', opacity: '0.5' },
          '100%': { transform: 'translateY(-20%)', opacity: '1' },
        },
        'slide-up': {
          '0%': { transform: 'translateY(100%)' },
          '100%': { transform: 'translateY(0)' },
        },
        'slide-down': {
          '0%': { transform: 'translateY(0)' },
          '100%': { transform: 'translateY(100%)' },
        },
      },
      animation: {
        'slide-up': 'slide-up 0.2s ease-out forwards',
        'slide-down': 'slide-down 0.2s ease-out forwards',
        'modal-short-slide-up': 'short-slide-up 0.2s ease-out forwards',
      },
      boxShadow: {
        'inner-strong': 'inset 10px 10px 20px rgba(255, 236, 199, 0.3)',
        'up-shadow': '0px -4px 4px 0px rgba(197, 197, 197, 0.25)',
      },
    },
  },
  plugins: [],
}
export default config
