//
//  AppDelegate.h
//  testapp
//
//  Created by Alex Vasilev on 4/5/15.
//  Copyright (c) 2015 Alex Vasilev. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;
- (void)processMessage:(NSValue*) wrappedPtr;
@end

extern AppDelegate* gTheAppDelegate;

